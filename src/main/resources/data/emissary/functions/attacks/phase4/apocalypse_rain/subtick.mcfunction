#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.timer 1
tag @s[scores={em.timer=60..}] add end
execute as @s[tag=projectile,tag=!end] at @s run function emissary:attacks/phase4/apocalypse_rain/__generated__/block/689
execute as @s[tag=end] run function emissary:attacks/phase4/apocalypse_rain/__generated__/block/690