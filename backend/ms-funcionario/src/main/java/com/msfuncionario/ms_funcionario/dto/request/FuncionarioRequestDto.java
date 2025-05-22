package com.msfuncionario.ms_funcionario..dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class FuncionarioRequestDto {

    private Long id;

    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "Email inválido")
    @Size(min = 9, max = 100, message = "Email deve ter entre 9 e 100 caracteres")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Email deve conter apenas letras minúsculas, números, sublinhados (_), hífens (-), e pontos (.)")
    private String email;

    @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u00FF\\s]+$", message = "Nome inválido")
    @Size(min = 4, max = 100, message = "Nome deve ter entre 4 e 100 caracteres")
    private String nome;

    @Pattern(regexp = "^\\d{11}$", message = "Telefone é obrigatório e deve conter exatamente 11 dígitos numéricos")
    private String telefone;

    private String senha;

    public FuncionarioRequestDto() {}

    public FuncionarioRequestDto(Long id, String cpf, String email, String nome, String telefone, String senha) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
