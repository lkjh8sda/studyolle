package com.studyolle.account;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;
    
    @Override
    public boolean supports(Class<?> aClass) {
        // SignUpForm타입의 인스턴스를 검사한다.
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override           //들어오는 객체를 담음, 바인딩 에러를 담음
    public void validate(Object object, Errors errors) {
        //ToDo email, nickname 중복여부를 검사할 껀데 리포지토리 필요함
        SignUpForm signUpForm = (SignUpForm)object;
        if(accountRepository.existsByEmail(signUpForm.getEmail())){
            //리포지토리에 이메일에 해당하는 값이 있으면 리젝트한다.
            errors.rejectValue("email","invalid.email",new Object[]{signUpForm.getEmail()}, "이미 사용중인 이메일 입니다");
        }
        if(accountRepository.existsByNickname(signUpForm.getNickname())){
            //리포지토리에 이메일에 해당하는 값이 있으면 리젝트한다.
            errors.rejectValue("nickname","invalid.nickname",new Object[]{signUpForm.getNickname()}, "이미 사용중인 닉네임 입니다");
        }
    }
}
