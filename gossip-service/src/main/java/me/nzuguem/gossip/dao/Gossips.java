package me.nzuguem.gossip.dao;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public record Gossips() {

    private static final List<String> GOSSIPS = List.of(
            "🚀 On murmure que le sprint va encore déborder... Comme d'habitude.",
            "🎯 Le Product Owner veut 'juste un petit changement'. Spoiler: c'est jamais petit.",
            "🐛 Quelqu'un a poussé en prod un vendredi après-midi. L'équipe DevOps est en PLS.",
            "☕ La machine à café a été détartrée ! C'est Noël avant l'heure.",
            "🍕 Pizza offerte vendredi ! Mais faut finir toutes les stories avant...",
            "🔥 Les tests passent en local mais pas en CI. Le mystère reste entier.",
            "💻 Le stagiaire a drop la base de prod. Mais c'était un accident, promis.",
            "🎮 Le lead dev joue à Minecraft pendant les daily. Personne n'ose rien dire.",
            "📊 Les métriques sont au vert ! Bon, personne ne sait vraiment ce qu'elles mesurent.",
            "🏆 L'équipe a battu son record de dette technique. Bravo ?",
            "🤖 L'IA va tous nous remplacer. Mais elle commit aussi du code pourri.",
            "📱 Le client a changé d'avis. Encore. Pour la 5ème fois cette semaine.",
            "🎨 Le designer a redesigné toute l'app. Sans prévenir. Encore.",
            "⏰ La réunion de 15 minutes a duré 2 heures. Un nouveau record !",
            "🔒 Quelqu'un a commit ses credentials AWS. Les tokens tournent.",
            "🎤 Le manager veut faire un 'quick sync'. Prépare-toi, ça va durer.",
            "🏃 Le legacy code court plus vite que notre nouveau microservice.",
            "🌙 Quelqu'un code encore à 3h du matin. Le café ne fait plus effet.",
            "📝 La documentation ? Quelle documentation ? Le code s'auto-explique, voyons.",
            "🎭 Le pair programming s'est transformé en débat philosophique sur les tabs vs spaces."
    );

    public static String random() {
        return GOSSIPS.get(ThreadLocalRandom.current().nextInt(GOSSIPS.size()));
    }
}
