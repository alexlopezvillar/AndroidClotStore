package com.example.clotstore.CarretFactura


import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorFactura
import com.example.clotstore.FragmentsMenu.Menu


import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityFacturaBinding
import java.io.File
import java.io.FileOutputStream

class Factura : AppCompatActivity() {

    private val NOTIFICATION_ID = 1

    private lateinit var adapter: AdaptadorFactura
    lateinit var channelId: String

    private lateinit var binding: ActivityFacturaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        var preuTotal: String
        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            ContextCompat.startActivity(this, intent, null)

        }
        binding.rvFactura.layoutManager = LinearLayoutManager(this)
        adapter = AdaptadorFactura(llistaProductesCarret)
        binding.rvFactura.adapter = adapter
        binding.rvFactura.layoutManager = LinearLayoutManager(this)

        binding.PreuFinal.setText(
            "Preu Total + IVA inclós: " + String.format(
                "%.2f",
                carret.PreuTotal
            ) + "€"
        )
        preuTotal = "Preu Total: " + String.format(
            "%.2f",
            carret.PreuTotal
        ) + " €"
        binding.btImprimeixPDF.setOnClickListener() {
            generatePDF(this, binding.rvFactura, preuTotal)
        }
    }

    fun generatePDF(context: Context, recyclerView: RecyclerView, preuTotal: String) {
        // Verificar si se tienen los permisos de escritura en el almacenamiento externo
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no se tienen los permisos, solicitarlos
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE
            )
            return
        }

        // Crear el documento PDF
        val document = PdfDocument()

        // Crear una página en el documento PDF
        val pageInfo = PdfDocument.PageInfo.Builder(
            1150,
            3000,
            1
        ).create()
        val page = document.startPage(pageInfo)

        // Obtener el canvas de la página
        val canvas: Canvas = page.canvas

        // Dibujar el logo y el texto antes del RecyclerView
        val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logoempresa)
        val logoWidth = 200
        val logoHeight = 200
        val logoLeft = 50
        val logoTop = 50
        canvas.drawBitmap(logo, null, Rect(logoLeft, logoTop, logoLeft + logoWidth, logoTop + logoHeight), null)

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 52f
            textAlign = Paint.Align.LEFT
        }
        val textLeft = logoLeft + logoWidth + 20
        val textTop = logoTop + logoHeight / 2 + textPaint.textSize / 2
        canvas.drawText("ClotStore", textLeft.toFloat(), textTop, textPaint)

        // Obtener el bitmap del RecyclerView
        val recyclerViewBitmap = Bitmap.createBitmap(
            recyclerView.width,
            recyclerView.height,
            Bitmap.Config.ARGB_8888
        )
        val recyclerViewCanvas = Canvas(recyclerViewBitmap)
        recyclerView.draw(recyclerViewCanvas)

        // Guardar la posición actual del RecyclerView
        val savedPosition = recyclerView.scrollY

        // Desplazar el RecyclerView a la posición inicial para que se dibuje correctamente
        recyclerView.scrollToPosition(0)

        // Dibujar el RecyclerView en el canvas
        val recyclerViewLeft = 50
        val recyclerViewTop = logoTop + logoHeight + 50
        canvas.drawBitmap(
            recyclerViewBitmap,
            recyclerViewLeft.toFloat(),
            recyclerViewTop.toFloat(),
            null
        )

        // Dibujar el precio total debajo del RecyclerView
        val totalText = "$preuTotal" // Texto a dibujar
        val totalTextBounds = Rect()
        textPaint.getTextBounds(totalText, 0, totalText.length, totalTextBounds)
        val totalTextX = canvas.width / 2 // Posición horizontal centrada
        val totalTextY =
            recyclerViewTop + recyclerView.height + totalTextBounds.height() + 80 // Posición vertical debajo del RecyclerView

        // Dibujar el precio total debajo del RecyclerView
        canvas.drawText(
            totalText,
            totalTextX.toFloat(),
            totalTextY.toFloat(),
            textPaint
        )

        // Finalizar la página
        document.finishPage(page)

        // Crear el directorio de descarga si no existe
        val downloadDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "FacturaPDF"
        )
        if (!downloadDir.exists()) {
            downloadDir.mkdirs()
        }

        // Crear el archivo PDF en el directorio de descarga
        val file = File(downloadDir, "Factura.pdf")

        // Guardar el documento PDF en el archivo
        try {
            val fileOutputStream = FileOutputStream(file)
            document.writeTo(fileOutputStream)
            fileOutputStream.close()

            // Mostrar mensaje de éxito
            Toast.makeText(context, "Factura descarregada correctament", Toast.LENGTH_SHORT).show()

            // Mostrar notificación para abrir el archivo PDF manualmente
            val openIntent = Intent(Intent.ACTION_VIEW)
            val pdfUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            openIntent.setDataAndType(pdfUri, "application/pdf")
            openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val notification = NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.logoempresa)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.logoempresa
                    )
                )
                .setContentTitle("Factura descarregada")
                .setContentText("Fes clic per obrir el PDF")
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        openIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                .setAutoCancel(true)
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "channel_id",
                    "PDF",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notification)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al generar el PDF", Toast.LENGTH_SHORT).show()
        }

        // Restaurar la posición original del RecyclerView
        recyclerView.scrollToPosition(savedPosition)

        // Cerrar el documento PDF
        document.close()
    }

    override fun onBackPressed(){

    }


}

