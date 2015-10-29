package ro.efidelity.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class BASE64DecodedMultipartFile implements MultipartFile {
	private final byte[] continut;

	public BASE64DecodedMultipartFile(byte[] imgContent) {
		this.continut = imgContent;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getOriginalFilename() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return continut != null && continut.length > 0;
	}

	@Override
	public long getSize() {
		return continut.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return continut;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(continut);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		FileOutputStream fos = new FileOutputStream(dest);
		fos.write(continut);
		fos.close();
	}
}