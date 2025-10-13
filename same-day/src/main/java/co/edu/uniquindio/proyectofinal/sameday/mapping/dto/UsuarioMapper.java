package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNombreCompleto(u.getNombreCompleto());
        dto.setCorreo(u.getCorreo());
        dto.setTelefono(u.getTelefono());
        List<DireccionDTO> direcciones = u.getDireccionesFrecuentes() == null ? new ArrayList<>()
                : u.getDireccionesFrecuentes().stream().map(UsuarioMapper::direccionToDTO).collect(Collectors.toList());
        dto.setDirecciones(direcciones);
        dto.setMetodosPago(u.getMetodosPago());
        return dto;
    }

    public static Usuario toModel(UsuarioDTO dto) {
        if (dto == null) return null;
        List<Direccion> direcciones = dto.getDirecciones() == null ? new ArrayList<>()
                : dto.getDirecciones().stream().map(UsuarioMapper::direccionToModel).collect(Collectors.toList());
        return new Usuario(dto.getIdUsuario(), dto.getNombreCompleto(), dto.getCorreo(),
                dto.getTelefono(), direcciones, dto.getMetodosPago());
    }

    private static DireccionDTO direccionToDTO(Direccion d) {
        if (d == null) return null;
        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(d.getIdDireccion());
        dto.setAlias(d.getAlias());
        dto.setCalle(d.getCalle());
        dto.setCiudad(d.getCiudad());
        dto.setCoordenadas(d.getCoordenadas());
        return dto;
    }

    private static Direccion direccionToModel(DireccionDTO dto) {
        if (dto == null) return null;
        return new Direccion(dto.getIdDireccion(), dto.getAlias(), dto.getCalle(), dto.getCiudad(), dto.getCoordenadas());
    }
}
