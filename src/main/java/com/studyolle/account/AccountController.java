package com.studyolle.account;

import com.studyolle.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    //signUpForm을 받을 때 validation 자동 실행
    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){

        model.addAttribute("signUpForm",new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")                         // 바인딩이나 컨버젼시 에러 받는 타입
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        //아이디나 비번 형식 오류 검증
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        //중복 이메일이랑 닉네임 검증
        //initBinder로 검증


        Account account = accountService.processNewAccount(signUpForm);

        accountService.login(account);

        //todo 회원가입 처리
        return "redirect:/";
    }


    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if(account==null){
            model.addAttribute("error","wrong.email");
            return view;
        }
        if(!account.isValidToken(token)){
            model.addAttribute("error","wrong.token");
            return view;
        }
        account.completeSignUp();
    //로그인
        accountService.login(account);
        model.addAttribute("numberOfUser",accountRepository.count());
        model.addAttribute("nickname",account.getNickname());
        return view;
    }

}
