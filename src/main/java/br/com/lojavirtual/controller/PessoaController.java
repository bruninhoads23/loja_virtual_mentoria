package br.com.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.ExceptionMentoriaJava;
import br.com.lojavirtual.enums.TipoPessoa;
import br.com.lojavirtual.model.Endereco;
import br.com.lojavirtual.model.PessoaFisica;
import br.com.lojavirtual.model.PessoaJuridica;
import br.com.lojavirtual.model.dto.CepDTO;
import br.com.lojavirtual.model.dto.ConsultaCnpjDTO;
import br.com.lojavirtual.repository.EnderecoRepository;
import br.com.lojavirtual.repository.PessoaFisicaRepository;
import br.com.lojavirtual.repository.PessoaRepository;
import br.com.lojavirtual.service.PessoaUserService;
import br.com.lojavirtual.utils.ValidaCNPJ;
import br.com.lojavirtual.utils.ValidaCPF;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaUserService pessoaUserService;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	
	@ResponseBody
	@GetMapping(value="/consultaPfNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome){
		
		List<PessoaFisica> fisicas  = pessoaFisicaRepository.pesquisaPorNome(nome.trim().toUpperCase()); 
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@GetMapping(value="/consultaPfCpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf){
		
		List<PessoaFisica> fisicas  = pessoaFisicaRepository.pesquisaPorCpfPF(cpf); 
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value="/consultaNomePJ/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaNomePJ(@PathVariable("nome") String nome){
		
		List<PessoaJuridica> juridica  = pessoaRepository.pesquisaPorNome(nome.trim().toUpperCase()); 
		
		return new ResponseEntity<List<PessoaJuridica>>(juridica, HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@GetMapping(value="/consultaCnpjPJ/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaCnpjPJ(@PathVariable("cnpj") String cnpj){
		
		List<PessoaJuridica> juridica  = pessoaRepository.existeCnpjCadastradoList(cnpj.toString().toUpperCase()); 
		
		return new ResponseEntity<List<PessoaJuridica>>(juridica, HttpStatus.OK);
		
	}
	
	

	@ResponseBody
	@PostMapping(value = "/salvarPJ")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica)
			throws ExceptionMentoriaJava {

		if (pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa jurídica não pode ser null");

		}
		
		if (pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionMentoriaJava("Informe o Tipo Jurídico ou Fornecedor da Loja");

		}

		/* verifica se esta cadastrando uma pessoa nova com cnpj que já existe */
		if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {

			throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número" + pessoaJuridica.getCnpj());
		}

		if (pessoaJuridica.getId() == null
				&& pessoaRepository.existeInsEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava(
					"Já existe Inscrição estadual cadastrado com o número: " + pessoaJuridica.getInscEstadual());
		}

		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("Cnpj : " + pessoaJuridica.getCnpj() + " está inválido.");
		}

		//pessoa juridica for igual o null ou id dela nao exisitr
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

				CepDTO cepDTO = pessoaUserService.consultaCEP(pessoaJuridica.getEnderecos().get(p).getCep());

				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());

			}
		} else {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();

				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {

					CepDTO cepDTO = pessoaUserService.consultaCEP(pessoaJuridica.getEnderecos().get(p).getCep());

					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				}
			}
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}

	
	
	@ResponseBody
	@PostMapping(value = "/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica)
			throws ExceptionMentoriaJava {

		if (pessoaFisica.getTipoPessoa() == null) {
			throw new ExceptionMentoriaJava("Pessoa física não pode ser null");

		}
		
		if (pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());

		}

		/* verifica se esta cadastrando uma pessoa nova com cnpj que já existe */
		if (pessoaFisica.getId() == null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {

			throw new ExceptionMentoriaJava("Já existe Cpf cadastrado com o número" + pessoaFisica.getCpf());
		}

		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("Cnpj : " + pessoaFisica.getCpf() + " está inválido.");
		}

		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {

		///// OUUUUUUUU
		// CepDTO cepDTO = pessoaUserService.consultaCEP(cep);
		// return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);

		/// OUUUUUUUUU
		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCEP(cep), HttpStatus.OK);

	}
	
	
	@ResponseBody
	@GetMapping(value = "/consultaCnpj/{cnpj}")
	public ResponseEntity<ConsultaCnpjDTO> consultaCnpj(@PathVariable("cnpj") String cnpj) {

		///// OUUUUUUUU
		// CepDTO cepDTO = pessoaUserService.consultaCEP(cep);
		// return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);

		/// OUUUUUUUUU
		return new ResponseEntity<ConsultaCnpjDTO>(pessoaUserService.consultaCnpjReceitaWS(cnpj), HttpStatus.OK);

	}

}
