package com.nseit.generic.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;




public class CandDocPdfTOImageConverter {
	private Logger logger = LoggerHome.getLogger(getClass());
	//final PdfPage page;
	public void convert(String sourceDir,String fileName,String destinationDir,ServletContext context){
		PDDocument document=null;
		try{

			File outputfile=null;
			document = PDDocument.load(sourceDir);
			List<PDPage> list = document.getDocumentCatalog().getAllPages();
			System.out.println("Total files to be converted -> "+ list.size());
			ExecutorService executor = (ExecutorService)context.getAttribute("executor"); //Executors.newFixedThreadPool(2);
			List<Future<String>> list1 = new ArrayList<Future<String>>();
			//String fileName = sourceFile.getName().replace(".pdf", "");             
			int pageNumber = 1;
			for (PDPage page : list) {
			
				// BufferedImage image = page.convertToImage();
				outputfile = new File(destinationDir + fileName+""+pageNumber+".jpg");
				list1.add(executor.submit(new ConvertPdfPageToPngCallable(page, outputfile)));
				pageNumber++;

			}

			/*for (Future<String> future: list1){
				try {
					logger.info("Thread for PDF to Image Conversion and Write to folder"+future.get());
				} catch (Exception e) {
					executor.shutdownNow();

				}

			}
			executor.shutdown();*/

		}catch(Exception e){
			logger.fatal(e,e);
		}finally{
			try {
				document.close();
			} catch (IOException e) {
				logger.fatal(e,e);
			}
		}
	}
	private class ConvertPdfPageToPngCallable implements Callable<String>{
		private PDPage page;
		private File outputFile;

		ConvertPdfPageToPngCallable(PDPage page,File outputFile){
			this.page=page;
			this.outputFile=outputFile;
		}
		@Override
		public String call() throws Exception {
		
			BufferedImage bim = page.convertToImage(); 
			ImageIO.write(bim, "jpg", outputFile);
			return "Success"+Thread.currentThread().getName();
		}

	}
}
