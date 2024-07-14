package med.voll.api.domain.usuarios;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(@NotNull
                                         Long id,
                                         String nombre,
                                         String telefono,
                                         DatosDireccion direccion) {
}
