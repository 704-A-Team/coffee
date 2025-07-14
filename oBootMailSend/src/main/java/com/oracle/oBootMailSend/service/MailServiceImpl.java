package com.oracle.oBootMailSend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	
	private JavaMailSender mailSender;
	
	// Pdf 생성 메소드
	public ByteArrayOutputStream generatePdf() throws IOException {
		PDDocument document = new PDDocument();
		PDPage	   page		= new PDPage();
		document.addPage(page);
		
		// PDF 페이지에 내용 쓰는 스트림
		PDPageContentStream content = new PDPageContentStream(document, page);
		content.beginText();			 // 텍스트 작성 시작
		content.setFont(PDType1Font.HELVETICA_BOLD, 12);
		content.newLineAtOffset(50, 700);
		content.showText("증명서");		// 텍스트 출력
		content.endText();				// 텍스트 작성 종료
		content.close();			    // 스트림 닫기
		
		
		// PDF 문서 전체를 파일이나 네트워크가 아닌 메모리(바이트 배열)에 저장하기 위한 스트림
		ByteArrayOutputStream baos = new ByteArrayOutputStream();	// 메모리 저장용 바이트 배열 스트림 생성
		document.save(baos);	// 문서를 baos에 저장 (파일이 아니라 메모리에 저장)
		document.close(); 	  	// 문서 닫기
		
		return baos;
		
	}
	
	// Mail전송 메소드
	public void mailTransport(ByteArrayOutputStream pdf) throws MessagingException {

		System.out.println("EmpController mailTransport Sending");
		String tomail = "nogangsss@naver.com";    // 받는 사람 이메일
		String setfrom = "nogangsss@gmail.com";	  // 이메일 주소 바꿔도 야물 세팅으로 간다
		String title = "mailTransport 입니다";	
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			helper.setTo(tomail);	// 받는사람 이메일
			helper.setSubject(title + "재직증명서 발급"); // 메일제목은 생략이 가능하다
		    helper.setText("안녕하세요 경민님, 재직증명서를 첨부합니다.");
			
		    helper.addAttachment("_certificate.pdf", new ByteArrayResource(pdf.toByteArray()));

			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

}
