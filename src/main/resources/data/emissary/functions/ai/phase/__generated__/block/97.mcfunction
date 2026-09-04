#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset Idle em.value
execute as @e[tag=emissary,tag=emissarymodel,tag=root] run function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 0
execute as @e[tag=emissary,tag=emissarymodel,tag=root] run function animated_java:ajemissary/animations/phase2_transition/tween_play
scoreboard players set EmissaryInvulnerable em.value 1