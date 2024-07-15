package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendadDeConsutaService {


    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadorDeConsultasList;

    @Autowired
    List<ValidadorCancelamientoDeConsulta> validadorCancelamientoDeConsulta;


    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {

        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("id del paciente no encontrado");
        }

        if(datos.idMedico() !=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrada");
        }

        validadorDeConsultasList.forEach(v -> v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);

        if(medico== null){
            throw new ValidacionDeIntegridad("Este aide para el paciente no fue encontrado");
        }
        var consulta = new Consulta(medico,paciente,datos.fecha());
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico() != null){
            return  medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DatosCancelamientoConsulta datosCancelamientoConsulta){
        if(!consultaRepository.existsById(datosCancelamientoConsulta.idConsulta())){
            throw new ValidacionDeIntegridad("Id de la consulta informado no existe!");

        }
        validadorCancelamientoDeConsulta.forEach(v -> v.validar(datosCancelamientoConsulta));
        var consulta = consultaRepository.getReferenceById(datosCancelamientoConsulta.idConsulta());
        consulta.cancelar(datosCancelamientoConsulta.motivo());
    }
}
