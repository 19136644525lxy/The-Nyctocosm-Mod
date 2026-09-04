#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 0
execute at @s run function animated_java:ajemissary/animations/phase4_flying_transition_idle/play