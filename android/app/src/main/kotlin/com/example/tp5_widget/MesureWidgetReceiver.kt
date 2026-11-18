package com.example.tp5_widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class MesureWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = MesureWidget()

    // Appelée quand le PREMIER widget est posé
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("TP5", "onEnabled appelée")
        WidgetWorker.planifier(context)
    }

    // Appelée quand un widget est supprimé (filet de sécurité)
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d("TP5", "onDeleted appelée : ${appWidgetIds.toList()}")
        val restants = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, MesureWidgetReceiver::class.java))
        if (restants.isEmpty()) {
            Log.d("TP5", "plus aucun widget : annulation du worker")
            WidgetWorker.annuler(context)
        }
    }

    // Appelée quand le DERNIER widget est retiré
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d("TP5", "onDisabled appelée")
        WidgetWorker.annuler(context)
    }
}