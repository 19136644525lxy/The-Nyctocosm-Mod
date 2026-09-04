#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set Idle em.value 1
scoreboard players set Emissary em.phase 3
execute as @e[tag=emissary,tag=emissarymodel,tag=root] run function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 5
execute as @e[tag=emissary,tag=emissarymodel,tag=root] run function animated_java:ajemissary/animations/phase3_idle/tween_play
function emissary:ai/hitbox/mode/phase3
scoreboard players reset EmissaryInvulnerable em.value