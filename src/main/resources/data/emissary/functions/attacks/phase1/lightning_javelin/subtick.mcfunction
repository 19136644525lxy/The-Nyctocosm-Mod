#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.value 1
execute if score @s em.value matches 20 run function emissary:attacks/phase1/lightning_javelin/throw
execute as @s[tag=!thrown] run function emissary:attacks/phase1/lightning_javelin/__generated__/block/50
execute as @s[tag=thrown] at @s run function emissary:attacks/phase1/lightning_javelin/slowcast
execute as @s[tag=thrown,tag=!end] at @s run function emissary:attacks/phase1/lightning_javelin/slowcast
execute as @s[tag=thrown,tag=!end] at @s run function emissary:attacks/phase1/lightning_javelin/slowcast
execute as @s[tag=thrown,tag=!end] at @s run function emissary:attacks/phase1/lightning_javelin/slowcast
execute as @s[tag=thrown,tag=!end] at @s run function emissary:attacks/phase1/lightning_javelin/slowcast
execute as @s[tag=thrown,tag=end] at @s run function emissary:attacks/phase1/lightning_javelin/impact
execute at @s positioned ^ ^ ^-10 run function effects:lightning/draw_spark