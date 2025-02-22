package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;

public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String documento,
        @NotNull
        Boolean activo,
        @NotBlank
        String telefono,
        @NotNull
        @Valid
        DatosDireccion direccion) {
}
