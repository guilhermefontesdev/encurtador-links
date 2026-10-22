package br.com.encurtador.encurtador.link;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.util.List;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class LinkService {

    private static final String ALFABETO = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TAMANHO_CODIGO = 7;
    private static final int MAX_TENTATIVAS = 5;

    private final SecureRandom random = new SecureRandom();
    private final LinkRepository repository;
    private final CliqueRepository cliqueRepository;
    private final String baseUrl;

    public LinkService(LinkRepository repository, CliqueRepository cliqueRepository,
                       @Value("${app.base-url}") String baseUrl){
        this.repository = repository;
        this.cliqueRepository = cliqueRepository;
        this.baseUrl = baseUrl;
    }

    @Transactional
    public LinkResponse encurtar(CriarLinkRequest request) {
        String code = gerarCodigoUnico();

        Link link = repository.save(new Link (code, request.url()));
        return paraResposta(link);
    }

    public List<LinkResponse> listar(){
        return repository.findAll(Sort.by(Sort.Direction.DESC, "criadoEm"))
                .stream()
                .map(this::paraResposta)
                .toList();
    }

    private String gerarCodigoUnico(){

        for(int i = 0; i < MAX_TENTATIVAS; i++){
            String code = gerarCodigo();
            if(!repository.existsByCode(code)){
                return code;
            }
        }
        throw new IllegalStateException("Nao foi possivel gerar um codigo unico");
    }

    private String gerarCodigo(){
        StringBuilder sb = new StringBuilder(TAMANHO_CODIGO);
        for(int i = 0; i<TAMANHO_CODIGO; i++ ){
        sb.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }
        return sb.toString();
    }

    private LinkResponse paraResposta(Link link){
        return new LinkResponse(
                link.getCode(),
                link.getUrlOriginal(),
                baseUrl + "/" + link.getCode(),
                link.getCriadoEm()
        );
    }

    @Transactional
    public Optional<String> registrarCliques (String code){

        Optional<Link> link = repository.findByCode(code);
       link.ifPresent(l -> cliqueRepository.save(new Clique(l)));
               return link.map(Link::getUrlOriginal);
    }

    @Transactional(readOnly = true)
    public Optional<EstatisticasResponse> estatisticas(String code){
        return repository.findByCode(code)
                .map(link -> new EstatisticasResponse(
                        link.getCode(),
                        cliqueRepository.countByLink((link)
                        ), cliqueRepository.contarPorDia(link)));
    }


}
