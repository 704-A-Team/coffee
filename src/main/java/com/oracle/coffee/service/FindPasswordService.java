// src/main/java/com/oracle/coffee/service/FindPasswordService.java
package com.oracle.coffee.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.security.user.repository.UserRepository;
import com.oracle.coffee.repository.EmpRepository;
import com.oracle.coffee.domain.Account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class FindPasswordService {

    private final EmpRepository empRepository;        // MyBatis Repo (EmpDto 반환)
    private final UserRepository userRepository;      // JPA Repo (Account)
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public Result resetEmpPasswordAndSend(int emp_code, String emp_name, String emp_tel) {
        EmpDto emp = empRepository.findByEmp_code(emp_code);
        if (emp == null || emp.getEmp_isdel() == 1) {
            return Result.fail("일치하는 정보가 없습니다.");
        }
        if (!safeEquals(emp_name, emp.getEmp_name())) {
            return Result.fail("일치하는 정보가 없습니다.");
        }

        String req = digitsOnly(emp_tel);
        String db  = digitsOnly(emp.getEmp_tel());
        if (!req.equals(db)) {
            return Result.fail("일치하는 정보가 없습니다.");
        }

        Account account = userRepository.findByUsername(String.valueOf(emp_code));
        if (account == null) {
            return Result.fail("일치하는 정보가 없습니다.");
        }

        account.setPassword(passwordEncoder.encode("1234567"));
        userRepository.save(account);

        try {
            String subject = "[Coffee ERP] 임시 비밀번호 안내";
            String body =
                "안녕하세요, " + emp.getEmp_name() + "님.\n\n" +
                "요청하신 계정의 비밀번호가 임시 비밀번호로 초기화되었습니다.\n" +
                "임시 비밀번호: 1234567\n\n" +
                "로그인 후 반드시 비밀번호를 변경해 주세요.\n" +
                "- 로그인 아이디(Username): " + emp_code + "\n";
            emailService.sendEmail(emp.getEmp_email(), subject, body);
            return Result.ok("임시 비밀번호를 이메일로 발송했습니다.");
        } catch (Exception e) {
            return Result.partial("비밀번호는 초기화되었으나 메일 발송에 실패했습니다.");
        }
    }

    private String digitsOnly(String s){ return s == null ? "" : s.replaceAll("\\D",""); }
    private boolean safeEquals(String a, String b){
        return (a == null ? "" : a.trim()).equals(b == null ? "" : b.trim());
    }

    public record Result(boolean success, String message, boolean mailFailed) {
        public static Result ok(String msg){ return new Result(true, msg, false); }
        public static Result partial(String msg){ return new Result(true, msg, true); }
        public static Result fail(String msg){ return new Result(false, msg, false); }
    }
}

