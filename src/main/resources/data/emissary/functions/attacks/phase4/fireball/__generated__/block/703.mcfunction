#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.timer 1
tag @s[scores={em.timer=60..}] add end
execute at @s[tag=!end] run function emissary:attacks/phase4/fireball/slowcast
execute at @s[tag=!end] run function emissary:attacks/phase4/fireball/slowcast
execute at @s[tag=end] run function emissary:attacks/phase4/fireball/impact