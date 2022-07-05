package com.siromgitsyou.minecraft.mineshotreforged.client.capture.task;

import java.nio.file.Path;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent.RenderTickEvent;

import com.siromgitsyou.minecraft.mineshotreforged.client.capture.FramebufferCapturer;
import com.siromgitsyou.minecraft.mineshotreforged.client.capture.FramebufferWriter;
import com.siromgitsyou.minecraft.mineshotreforged.client.config.MyModConfig;
import com.siromgitsyou.minecraft.mineshotreforged.util.reflection.PrivateAccessor;

public class CaptureTask implements RenderTickTask, PrivateAccessor {
	private static final Minecraft MC = Minecraft.getInstance();

	private final Path file;

	private int frame;
	private int displayWidth;
	private int displayHeight;

	public CaptureTask(Path file) {
		this.file = file;
	}

	@Override
	public boolean onRenderTick(RenderTickEvent evt) throws Exception {
		switch (frame) {
		// override viewport size (the following frame will be black)
		case 0:
			displayWidth = MC.getMainWindow().getFramebufferWidth();
			displayHeight = MC.getMainWindow().getFramebufferHeight();

			int width = MyModConfig.captureWidth;
			int height = MyModConfig.captureHeight;

			// resize viewport/framebuffer
			framebufferSizeUpdate(MC, width, height);

			break;

		// capture screenshot and restore viewport size
		case 3:
			try {
				FramebufferCapturer fbc = new FramebufferCapturer();
				FramebufferWriter fbw = new FramebufferWriter(file, fbc);
				fbw.write();
			} finally {
				// restore viewport/framebuffer
				framebufferSizeUpdate(MC, displayWidth, displayHeight);
			}
			break;
		}

		frame++;
		return frame > 3;
	}
}
