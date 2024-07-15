package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendadDeConsutaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private AgendadDeConsutaService service;
    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        var  response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idConsulta}")
    @Transactional
    public ResponseEntity cancelar(
            @PathVariable @Valid Long idConsulta,
            @RequestBody @Valid DatosCancelamientoConsulta datosCancelamiento
    ) {
        // Actualizar el idConsulta en la instancia existente
        datosCancelamiento = DatosCancelamientoConsulta.setIdConsulta(datosCancelamiento, idConsulta);
        service.cancelar(datosCancelamiento);
        return ResponseEntity.noContent().build();
    }

}

