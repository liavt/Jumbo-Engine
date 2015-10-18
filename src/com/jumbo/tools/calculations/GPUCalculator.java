package com.jumbo.tools.calculations;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLKernel;
import org.lwjgl.opencl.CLMem;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opencl.CLProgram;
import org.lwjgl.opencl.Util;

import com.jumbo.tools.loaders.StringHandler;

@Deprecated
class GPUCalculator {
	// OpenCL variables
	public static CLContext context;
	public static CLPlatform platform;
	public static List<CLDevice> devices;
	public static CLCommandQueue queue;
	private static CLKernel productKernel;
	private static int size = 2;
	private static FloatBuffer resultBuff = BufferUtils.createFloatBuffer(size);
	private static CLMem aMemory, bMemory, resultMemory;
	private static CLProgram program;

	public static void destroy() {
		CL10.clReleaseProgram(program);
		CL10.clReleaseMemObject(aMemory);
		CL10.clReleaseMemObject(bMemory);
		CL10.clReleaseMemObject(resultMemory);
		CL10.clReleaseContext(context);
		CL10.clReleaseCommandQueue(queue);
		CL.destroy();
	}

	final static int dimensions = 1;
	static PointerBuffer globalWorkSize = BufferUtils.createPointerBuffer(dimensions);

	public static FloatBuffer product() {
		int err = CL10.clEnqueueNDRangeKernel(queue, productKernel, dimensions, null, globalWorkSize, null, null, null);
		Util.checkCLError(err);
		err = CL10.clEnqueueReadBuffer(queue, resultMemory, CL10.CL_TRUE, 0, resultBuff, null, null);
		Util.checkCLError(err);

		CL10.clFinish(queue);
		return resultBuff;
	}

	static void setAMemory(float[] array) {
		aBuff = BufferUtils.createFloatBuffer(array.length);
		aBuff.put(array);
		aBuff.rewind();
		int err = CL10.clEnqueueWriteBuffer(queue, aMemory, CL10.CL_TRUE, 0, aBuff, null, null);
		Util.checkCLError(err);
	}

	static void setBMemory(float[] array) {
		bBuff = BufferUtils.createFloatBuffer(array.length);
		bBuff.put(array);
		bBuff.rewind();
		int err = CL10.clEnqueueWriteBuffer(queue, bMemory, CL10.CL_TRUE, 0, bBuff, null, null);
		Util.checkCLError(err);
	}

	static void setResultSize(int size) {
		GPUCalculator.size = size;
		resultBuff = BufferUtils.createFloatBuffer(size);
	}

	private static IntBuffer errorBuff = BufferUtils.createIntBuffer(1);
	private static FloatBuffer aBuff = BufferUtils.createFloatBuffer(size), bBuff = BufferUtils.createFloatBuffer(size);

	public static void init() throws LWJGLException {
		CL.create();
		platform = CLPlatform.getPlatforms().get(0);
		devices = platform.getDevices(CL10.CL_DEVICE_TYPE_ALL);
		context = CLContext.create(platform, devices, errorBuff);
		// Util.checkCLError(errorBuff.get(0));
		queue = CL10.clCreateCommandQueue(context, devices.get(0), CL10.CL_QUEUE_PROFILING_ENABLE, errorBuff);
		Util.checkCLError(errorBuff.get(0));
		program = CL10.clCreateProgramWithSource(context,
				StringHandler.loadAsString("src/com/jumbo/tools/calculations/kernel.txt"), errorBuff);
		Util.checkCLError(errorBuff.get(0));
		int error = CL10.clBuildProgram(program, devices.get(0), "", null);
		Util.checkCLError(error);
		productKernel = CL10.clCreateKernel(program, "product", errorBuff);
		Util.checkCLError(errorBuff.get(0));
		aMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, aBuff, errorBuff);
		Util.checkCLError(errorBuff.get(0));
		bMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, bBuff, errorBuff);
		Util.checkCLError(errorBuff.get(0));
		resultMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_ONLY, size * 4, errorBuff);
		Util.checkCLError(errorBuff.get(0));
		productKernel.setArg(0, aMemory);
		productKernel.setArg(1, bMemory);
		productKernel.setArg(2, resultMemory);
		globalWorkSize.put(0, size);
		Util.checkCLError(errorBuff.get(0));
	}
}
