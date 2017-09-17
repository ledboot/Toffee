package com.ledboot.util;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Locale;
import android.os.Process;

/**
 * Created by terry on 10/28/14.
 */
public class Debuger {

    public static String TAG = "Debuger";

    public static String TAG_ERR = "WorldGoErr";

    public static final int LOG_LEVEL_VERBOSE = 1;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_WARRING = 4;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_WTF = 6;
    
    public static final String THIS_CLASS_NAME = Debuger.class.getName();

    private static boolean sTagDefaut = false;
    private static int sLogLevel = LOG_LEVEL_VERBOSE;
    private static final String DUMP_DATA = "/WorldGo/dump/AppData";
    private static final String DUMP_ANR = "/WorldGo/dump/Anr";
    

    public static void setTag(String tag) {
        TAG = tag;
    }

    private static boolean logPass(int level) {
        if (level >= sLogLevel) {
            return true ;
        } else {
            return false;
        }
    }

    public static void logV(String msg) {
        logV(TAG,msg);
    }
    public static void logV(String tag, String msg) {
        if (logPass(LOG_LEVEL_VERBOSE)) {
            Log.v(sTagDefaut ? TAG : tag, buildMessage(msg));
        }
    }

    public static void logD(String msg) {
        logD(TAG,msg);
    }
    public static void logD(String tag, String msg) {
        if (logPass(LOG_LEVEL_DEBUG)) {
            Log.d(sTagDefaut ? TAG : tag, buildMessage(msg));
        }
    }

    public static void logI(String msg) {
        logI(TAG,msg);
    }
    public static void logI(String tag, String msg) {
        if (logPass(LOG_LEVEL_INFO)) {
            Log.i(sTagDefaut ? TAG : tag, buildMessage(msg));
        }
    }

    public static void logW(String msg) {
        logW(TAG,msg);
    }
    public static void logW(String tag, String msg) {
        if (logPass(LOG_LEVEL_WARRING)) {
            Log.w(sTagDefaut ? TAG : tag, buildMessage(msg));
        }
    }

    public static void logE(String msg) {
        logE(TAG,msg);
    }
    public static void logE(String tag, String msg) {
        if (logPass(LOG_LEVEL_ERROR)) {
            Log.e(sTagDefaut ? TAG : tag, buildMessage(msg));
        }
    }

    public static void logWTF(String msg) {
        logWTF(TAG,msg);
    }
    public static void logWTF(String tag, String msg) {
        if (logPass(LOG_LEVEL_WTF)) {
            Log.wtf(sTagDefaut ? TAG : tag, buildMessage(msg));
        }
    }

    public static void mark() {
        mark("");
    }

    public static void mark(String msg) {
        mark(TAG,msg);
    }

    public static void mark(String tag, String msg) {
        String time = "mark at time=" + SystemClock.elapsedRealtime();
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String file = null;
        String className = null;
        String methodName = null;
        long lineNum = -1;

        for (int i = 2; i < trace.length; i++) {
            className = trace[i].getClassName();
            if (!className.equalsIgnoreCase(THIS_CLASS_NAME)) {
                file = trace[i].getFileName();
                methodName = trace[i].getMethodName();
                lineNum = trace[i].getLineNumber();
                break;
            }
        }

        String out = String.format(Locale.US,"<pid=%d><tid=%d>[%s][%s][%d]:%s",
                Process.myPid(),Thread.currentThread().getId(),file,methodName,lineNum,time+msg);
        Log.d(sTagDefaut ? TAG : tag,out);
    }

    public static void printCurrentStack() {
        printCurrentStack(TAG);
    }

    public static void printCurrentStack(String tag) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        for (int i = 2; i < trace.length; i++) {
            String className = trace[i].getClassName();
            if (!className.equalsIgnoreCase(THIS_CLASS_NAME)) {
                String file = trace[i].getFileName();
                String methodName = trace[i].getMethodName();
                long lineNum = trace[i].getLineNumber();
                String msg = String.format(Locale.US,"[%s][%s][%s][%d]",file,className,methodName,lineNum);
                Log.d(sTagDefaut ? TAG : tag,msg);
            }
        }
    }
    
    public static void logException(Exception e) {
        if (logPass(LOG_LEVEL_WARRING)) {
        	Log.w(TAG, Log.getStackTraceString(e));
        }
    }
    
    public static void logException(String tag , Exception e) {
        if (logPass(LOG_LEVEL_WARRING)) {
        	Log.w(tag, Log.getStackTraceString(e));
        }
    }

    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String file = null;
        String className = null;
        String methodName = null;
        long lineNum = -1;

        for (int i = 2; i < trace.length; i++) {
            className = trace[i].getClassName();
            if (!className.equalsIgnoreCase(THIS_CLASS_NAME)) {
                file = trace[i].getFileName();
                methodName = trace[i].getMethodName();
                lineNum = trace[i].getLineNumber();
                break;
            }
        }
        return String.format(Locale.US, "<pid=%d><tid=%s>[%s][%s][%d]:%s",
                Process.myPid(),Thread.currentThread().getName(),file,methodName,lineNum,msg);
    }
    
    public interface DumpDataCompletedListener {
    	public static final int DUMP_OK = 0;
    	public static final int DUMP_FAILED = 1;
    	public void onCompleted(int rlt);
    }
    
    public static void catchDumpFilesInDir(File dir , ArrayList<File> list) {
    	if(!dir.exists() || !dir.isDirectory()) {
    		return;
    	}
    	
    	File[] files = dir.listFiles();
    	for(File file:files) {
    		if(file.getName().startsWith(".")) {
    			continue;
    		}
    		
    		if(file.isDirectory()) {
    			catchDumpFilesInDir(file,list);
    		}else{
        		list.add(file);
        		Debuger.logD("add dump file="+file.getAbsolutePath());
    		}
    	}
    }
    
    public static void dumpFile(String src , String dst) {
    	Debuger.logD("dump 复制文件 src="+src+" dst="+dst);
    	File srcFile = new File(src);
    	File dstFile = new File(dst);
    	
    	if(!srcFile.exists() || !srcFile.canRead() || srcFile.isDirectory()) {
    		Debuger.logW("dump src文件："+src+" 不存在或不可读！");
    		return ;
    	}
    	
    	if(dstFile.exists()) {
    		dstFile.delete();
    	}
    	
    	try {
			dstFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debuger.logW("dump dst文件："+dst+" 操作不可执行！");
			return;
		}
    	
    	FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fi = new FileInputStream(srcFile);
            fo = new FileOutputStream(dstFile);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {
            e.printStackTrace();
            Debuger.logW("dump src文件："+src+" 操作异常！");
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean dumpPath(Context cxt , String srcPath, String dstPath) {
    	File pathDir = new File(srcPath);
    	if(!pathDir.exists() || !pathDir.isDirectory()) {
    		return false;
    	}
    	
    	File dumpDir = new File(dstPath);
    	
    	if(dumpDir.exists()) {
    		dumpDir.delete();
    	}
    	
    	dumpDir.mkdirs();
    	
    	ArrayList<File> dumpFiles = new ArrayList<File>();
    	catchDumpFilesInDir(pathDir,dumpFiles);
    	for(File file:dumpFiles) {
    		String src = file.getAbsolutePath();
    		String dst = src.replace(srcPath, dstPath);
    		File dstFile = new File(dst);
    		new File(dstFile.getParent()).mkdirs();
    		dumpFile(src,dst);
    	}

    	return true;
    }
    
    public static void dumpData(Context cxt , DumpDataCompletedListener listener) {
    	if(!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
    		if(listener != null) {
    			listener.onCompleted(DumpDataCompletedListener.DUMP_FAILED);
    		}
    		return;
    	}
        File dumpDataDir = new File(Environment.getExternalStorageDirectory()+DUMP_DATA);
        if(!dumpDataDir.exists()){
            dumpDataDir.mkdirs();
        }
        File dumpAnrDir = new File(Environment.getExternalStorageDirectory()+DUMP_ANR);
        if(!dumpAnrDir.exists()){
            dumpAnrDir.mkdirs();
        }
    	
    	String pkgName = cxt.getPackageName();
    	String dataPath = "/data/data/"+pkgName;
    	if(!dumpPath(cxt,dataPath,Environment.getExternalStorageDirectory()+DUMP_DATA)) {
    		listener.onCompleted(DumpDataCompletedListener.DUMP_FAILED);
    	}

    	if(!dumpPath(cxt,"/data/anr",Environment.getExternalStorageDirectory()+DUMP_ANR)) {
    		listener.onCompleted(DumpDataCompletedListener.DUMP_FAILED);
    	}
    	
    	listener.onCompleted(DumpDataCompletedListener.DUMP_OK);
    }
    
    static boolean sDumpGoOn = true;
    public static void dumpLog(String tag , String preMsg , String path) {
    	Debuger.logW("start dump log");
    	java.lang.Process process = null;
    	
        try {
        	sDumpGoOn = true;
        	if(tag != null && !tag.trim().equals("") ) {
        		process=Runtime.getRuntime().exec("logcat -s "+tag);
        	}else{
        		process=Runtime.getRuntime().exec("logcat ");
        	}

			final java.lang.Process theProcess = process;
			new Thread(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(3 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//					Debuger.logW("dump log over!");
//			        if(theProcess != null) {
//			        	theProcess.destroy();
//			        } 
					sDumpGoOn = false;
					
					try {
						Thread.sleep(1 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			        if(theProcess != null) {
			        	Debuger.logW("dump log over!");
			        	theProcess.destroy();
			        } 
				}
				
			}.start();
			
        	File logFile = new File(path);
        	if(logFile.exists()) {
        		logFile.delete();
        	}
        	
        	logFile.createNewFile();
        	
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream()));  
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile))); 
            bufferedWriter.write(preMsg);
            String str = "";

            long logSize = 0;
            while(logSize < 10000 && sDumpGoOn && (str=bufferedReader.readLine())!=null )    //开始读取日志，每次读取一行
            {
            	logSize += str.length();
            	bufferedWriter.write(str+"\n\r");
            	str = null;
            }
            
            bufferedReader.close();
            bufferedWriter.close();

		} catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
	        if(process != null) {
	        	process.destroy();
	        	process = null;
	        } 
		}finally{
			Debuger.logW("dump log over!");
	        if(process != null) {
	        	process.destroy();
	        	process = null;
	        } 
		}
        
    }
}
