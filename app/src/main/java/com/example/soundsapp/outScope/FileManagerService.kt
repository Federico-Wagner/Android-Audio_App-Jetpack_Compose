package com.example.soundsapp.outScope

//import android.net.Uri
//import android.os.Environment
//import java.io.*
//import java.net.URI
//
//object FileManagerService {
//
//    fun savefileV2(sourceuri: Uri?, newFileName: String): String {
//        println("################ sourceuri #############################")
//        println(sourceuri)
//        println("################ sourceuri #############################")
//        println("################ sourceuri.path #############################")
//        val sourceFilename: String = sourceuri?.path.toString()
//        println(sourceuri?.path)
//        println("################ sourceuri.path #############################")
//        val destinationFilename =
////            Environment.getExternalStorageDirectory().path + File.separatorChar.toString() + newFileName
//            Environment.getDataDirectory().path + File.separatorChar.toString() + newFileName
//        var bis: BufferedInputStream? = null
//        var bos: BufferedOutputStream? = null
//        try {
//            bis = BufferedInputStream(FileInputStream(sourceFilename))
//            bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
//            val buf = ByteArray(1024)
//            bis.read(buf)
//            do {
//                bos.write(buf)
//            } while (bis.read(buf) != -1)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            try {
//                if (bis != null) bis.close()
//                if (bos != null) bos.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//        return destinationFilename
//    }
//
//    fun savefile(sourceuri: URI, newFileName: String): String {
//        println("################ sourceuri #############################")
//        println(sourceuri)
//        println("################ sourceuri #############################")
//        println("################ sourceuri.path #############################")
//        val sourceFilename: String = sourceuri.path
//        println(sourceuri.path)
//        println("################ sourceuri.path #############################")
//        val destinationFilename =
////            Environment.getExternalStorageDirectory().path + File.separatorChar.toString() + newFileName
//        Environment.getDataDirectory().path + File.separatorChar.toString() + newFileName
//        var bis: BufferedInputStream? = null
//        var bos: BufferedOutputStream? = null
//        try {
//            bis = BufferedInputStream(FileInputStream(sourceFilename))
//            bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
//            val buf = ByteArray(1024)
//            bis.read(buf)
//            do {
//                bos.write(buf)
//            } while (bis.read(buf) != -1)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            try {
//                if (bis != null) bis.close()
//                if (bos != null) bos.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//        return destinationFilename
//    }
//
//    fun UritoURI(Uriinput: Uri?): URI {
//        return URI(
//            Uriinput?.scheme,
//            Uriinput?.authority,
//            Uriinput?.path,
//            Uriinput?.query,
//            Uriinput?.fragment
//        )
//    }
//
//    fun URItoUri(URIinput: URI): Uri{
//        return Uri.parse(URIinput.toString())
//    }
//
//}
