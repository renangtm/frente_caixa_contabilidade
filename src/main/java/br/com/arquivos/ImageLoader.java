package br.com.arquivos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

public class ImageLoader extends SwingWorker<Void, Void> {

	private static Map<String, Map<Integer, Map<Integer, ImageIcon>>> cache = new HashMap<String, Map<Integer, Map<Integer, ImageIcon>>>();

	private ImageLoaderListener listener;
	private String imgUrl;
	private int width;
	private int height;

	public ImageLoader(String url, ImageLoaderListener listener, int width, int height) {

		this.listener = listener;
		this.imgUrl = url;
		this.width = width;
		this.height = height;

	}

	@Override
	protected Void doInBackground() throws Exception {

		try {
			if (ImageLoader.cache.get(this.imgUrl).get(this.width).get(this.height) != null) {

				this.listener.onLoad(ImageLoader.cache.get(this.imgUrl).get(this.width).get(this.height));

			} else {
				throw new RuntimeException("");
			}
		} catch (Exception ex) {

			try {
				
				BufferedImage bf = ImageIO.read(new URL(this.imgUrl));
				System.out.println(this.imgUrl+" --- "+bf.getWidth()+", "+bf.getHeight());

				ImageIcon img = new ImageIcon(bf.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH));

				if (ImageLoader.cache.get(this.imgUrl) == null) {
					ImageLoader.cache.put(this.imgUrl, new HashMap<Integer, Map<Integer, ImageIcon>>());
				}

				if (ImageLoader.cache.get(this.imgUrl).get(this.width) == null) {
					ImageLoader.cache.get(this.imgUrl).put(this.width, new HashMap<Integer, ImageIcon>());
				}

				ImageLoader.cache.get(this.imgUrl).get(this.width).put(this.height, img);

				this.listener.onLoad(img);

			} catch (Exception e) {
				this.listener.onFail();
			}
		}
		
		return null;
	}

}
