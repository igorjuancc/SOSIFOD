package br.com.sosifod.ws;

import br.com.sosifod.bean.Intimacao;
import br.com.sosifod.dto.IntimacaoDto;
import br.com.sosifod.dto.OficialDto;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.facade.IntimacaoFacade;
import br.com.sosifod.facade.OficialFacade;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/sosifod")
public class SosifodResource {

    @Context
    private UriInfo context;

    public SosifodResource() {
    }
 
    @GET
    @Path("/oficiais")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaOficiais() throws DaoException {
        List<OficialDto> oficiais = OficialFacade.listaOficiaisDto();
        GenericEntity<List<OficialDto>> gt = new GenericEntity<List<OficialDto>>(oficiais){};        
        return Response.ok().entity(gt).build();
    }
    
    @POST
    @Path("/novaIntimacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response novaIntimacao(IntimacaoDto intimacaoDto) throws DaoException {
        Intimacao intimacao = IntimacaoFacade.cadastrarIntimacaoDto(intimacaoDto);  
        intimacaoDto.setId(intimacao.getId());
        return Response.ok().entity(intimacaoDto).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
