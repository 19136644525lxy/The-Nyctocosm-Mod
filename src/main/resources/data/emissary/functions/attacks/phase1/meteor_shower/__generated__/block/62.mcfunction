#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.value 1
execute if score @s em.value matches 100.. run tag @s add end
execute as @s[tag=projectile,tag=!end] at @s run function emissary:attacks/phase1/meteor_shower/slowcast
execute as @s[tag=projectile,tag=!end] at @s run function emissary:attacks/phase1/meteor_shower/slowcast
execute as @s[tag=projectile,tag=end] run function emissary:attacks/phase1/meteor_shower/impact