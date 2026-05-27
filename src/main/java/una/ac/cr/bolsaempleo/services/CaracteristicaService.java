package una.ac.cr.bolsaempleo.services;

import una.ac.cr.bolsaempleo.models.Caracteristica;
import una.ac.cr.bolsaempleo.repositories.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    public List<Caracteristica> obtenerTodas() {
        return caracteristicaRepository.findAll();
    }

    public List<Caracteristica> obtenerRaices() {
        return caracteristicaRepository.findByPadreIsNull();
    }

    public List<Caracteristica> obtenerHijos(Long padreId) {
        return caracteristicaRepository.findByPadreId(padreId);
    }

    public Caracteristica obtenerPorId(Long id) {
        return caracteristicaRepository.findById(id).orElse(null);
    }

    public Caracteristica guardar(Caracteristica caracteristica) {
        return caracteristicaRepository.save(caracteristica);
    }

    public void eliminar(Long id) {
        caracteristicaRepository.deleteById(id);
    }
}