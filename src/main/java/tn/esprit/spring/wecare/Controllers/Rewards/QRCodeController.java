package tn.esprit.spring.wecare.Controllers.Rewards;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.Rewards.QRCodeGenerator;

@RestController
public class QRCodeController {
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode2.png";

	
    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}/{width}/{height}")
		public void download(
				@PathVariable("codeText") String codeText,
				@PathVariable("width") Integer width,
				@PathVariable("height") Integer height)
			    throws Exception {
			        QRCodeGenerator.generateQRCodeImage(codeText, width, height, QR_CODE_IMAGE_PATH);
			    }

    
}