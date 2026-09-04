#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set Attacking em.value 0
scoreboard players set Idle em.value 1
function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 5
execute if score Emissary em.phase matches 1 run function animated_java:ajemissary/animations/phase1_idle/tween_play
execute if score Emissary em.phase matches 2 run function animated_java:ajemissary/animations/phase2_idle/tween_play
execute if score Emissary em.phase matches 3 run function animated_java:ajemissary/animations/phase3_idle/tween_play
execute if score Emissary em.phase matches 4 unless score Flying em.value matches 1 run function animated_java:ajemissary/animations/phase4_idle/play
execute if score Emissary em.phase matches 4 if score Flying em.value matches 1 run function animated_java:ajemissary/animations/phase4_idle_flying/play