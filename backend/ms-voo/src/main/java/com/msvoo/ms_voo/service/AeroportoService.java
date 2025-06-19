package main.java.com.msvoo.ms_voo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msvoo.ms_voo.dto.CodigoAeroportoDto;
import com.msvoo.ms_voo.exceptions.ListaAeroportoVaziaException;
import com.msvoo.ms_voo.model.Aeroporto;
import com.msvoo.ms_voo.repository.AeroportoRepository;

@Service
public class AeroportoService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AeroportoRepository aeroportoRepository;

    public List<CodigoAeroportoDto> listarAeroportos() throws ListaAeroportoVaziaException {
        List<Aeroporto> listaAeroportoBD = aeroportoRepository.findAll();
        if (listaAeroportoBD.isEmpty()) {
            throw new ListaAeroportoVaziaException("Lista de aeroportos vazia!");
        }

        List<CodigoAeroportoDto> listaAeroportoDto = listaAeroportoBD.stream().map(aeroportoBD -> mapper.map(aeroportoBD, CodigoAeroportoDto.class)).collect(Collectors.toList());
        return listaAeroportoDto;
    }
}
