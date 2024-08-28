package com.orange.vinicola.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidador implements ConstraintValidator<CPFValido, String> {

    @Override
    public void initialize(CPFValido constraint) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        //TODO: Implementar validação de CPF

        return true;
    }
}
