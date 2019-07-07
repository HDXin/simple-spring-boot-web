package top.atstudy.framework.kit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public abstract class ClassKit {
    public ClassKit() {
    }

    public static <T> Class<T> getGenericType(Class<?> target) {
        return getGenericType(target, 0);
    }

    public static <T> Class<T> getGenericType(Class<?> target, Integer index) {
        Type t = target.getGenericSuperclass();
        ParameterizedType p = (ParameterizedType)t;
        Class<T> classType = (Class)p.getActualTypeArguments()[index];
        return classType;
    }

    public static Object dynamicLoadClass(String package_, String className, String code) {
        String root = ClassKit.class.getResource("/").getPath();
        String classPath = getEcologicalClassPath(root);
        Class dynamicClass = null;
        Object o = null;

        try {
            dynamicClass = Class.forName(package_ + "." + className);
        } catch (ClassNotFoundException var15) {
            ;
        }

        if (dynamicClass == null) {
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            JavaFileObject fileObject = new ClassKit.JavaStringObject(className, code);
            CompilationTask task = javaCompiler.getTask((Writer)null, (JavaFileManager)null, (DiagnosticListener)null, Arrays.asList("-cp", classPath, "-d", root), (Iterable)null, Arrays.asList(fileObject));
            task.call();
            ClassKit.DynamicClassLoader classLoader = new ClassKit.DynamicClassLoader(root);

            try {
                dynamicClass = classLoader.loadClass(package_ + "." + className);
            } catch (ClassNotFoundException var14) {
                var14.printStackTrace();
            }
        }

        try {
            o = ClassKit.class.getClassLoader().loadClass(dynamicClass.getCanonicalName()).newInstance();
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException var13) {
            var13.printStackTrace();
        }

        return o;
    }

    private static String getEcologicalClassPath(String root) {
        String classPath = System.getProperty("java.class.path");
        String[] libs = (new File(root + "/../lib/")).list();
        if (libs != null) {
            StringBuffer libsB = new StringBuffer();
            Arrays.asList(libs).forEach((v) -> {
                libsB.append("${classPath}/../lib/" + v + ":");
            });
            String libsClassPath = libsB.toString();
            libsClassPath = libsClassPath.substring(0, libsClassPath.length() - 1);
            libsClassPath = libsClassPath.replace("${classPath}", root);
            classPath = classPath + ":" + root.substring(0, root.length() - 1) + ":" + libsClassPath;
        }

        return classPath;
    }

    public static void copyProperty(Object origin, Object target) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class newClass = target.getClass();
        Class oldClass = origin.getClass();
        Field[] newFields = newClass.getDeclaredFields();
        Field newField = null;
        Field oldField = null;
        Field[] var7 = newFields;
        int var8 = newFields.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            Field f = var7[var9];
            String fieldName = f.getName();
            newField = newClass.getDeclaredField(fieldName);
            newField.setAccessible(true);
            Object newObject = newField.get(target);
            if (!isContinue(newObject)) {
                oldField = oldClass.getDeclaredField(fieldName);
                oldField.setAccessible(true);
                oldField.set(origin, newObject);
            }
        }

    }

    private static boolean isContinue(Object object) {
        if (object != null && !"".equals(object)) {
            String valueStr = object.toString();
            return "0".equals(valueStr) || "0.0".equals(valueStr);
        } else {
            return true;
        }
    }

    private static class JavaStringObject extends SimpleJavaFileObject {
        private String code;

        public JavaStringObject(String name, String code) {
            super(URI.create(name + ".java"), Kind.SOURCE);
            this.code = code;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.code;
        }
    }

    private static class DynamicClassLoader extends ClassLoader {
        public String classPath;

        public DynamicClassLoader(String classPath) {
            this.classPath = classPath;
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Class result;
            try {
                result = Class.forName(name);
            } catch (ClassNotFoundException var5) {
                ByteArrayOutputStream bos = this.getClassOutputStream(name);
                result = this.defineClass((String)null, bos.toByteArray(), 0, bos.toByteArray().length);
            }

            return result;
        }

        private ByteArrayOutputStream getClassOutputStream(String name) throws ClassNotFoundException {
            String fileName = name.replace(".", "/") + ".class";
            File classFile = new File(this.classPath, fileName);
            if (!classFile.exists()) {
                throw new ClassNotFoundException(classFile.getPath() + " 不存在");
            } else {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ByteBuffer bf = ByteBuffer.allocate(1024);
                FileInputStream fis = null;
                FileChannel fc = null;

                try {
                    fis = new FileInputStream(classFile);
                    fc = fis.getChannel();

                    while(fc.read(bf) > 0) {
                        bf.flip();
                        bos.write(bf.array(), 0, bf.limit());
                        bf.clear();
                    }
                } catch (IOException var17) {
                    var17.printStackTrace();
                } finally {
                    try {
                        fis.close();
                        fc.close();
                    } catch (IOException var16) {
                        var16.printStackTrace();
                    }

                }

                return bos;
            }
        }
    }
}
