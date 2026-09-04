#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.timer 1
execute as @s[tag=!stop] run function emissary:attacks/phase2/sword_plunge/slowcast
execute as @s[tag=!stop] at @s run function emissary:attacks/phase2/sword_plunge/slowcast
execute as @s[tag=!stop] at @s run function emissary:attacks/phase2/sword_plunge/slowcast
execute as @a[tag=impact_swordplunge] at @s run function emissary:attacks/phase2/sword_plunge/impact
execute at @s positioned ~ ~-60 ~ run particle flash ~ ~ ~ 1 1 1 0 1 force
execute at @s positioned ~ ~-60 ~ run particle cloud ~ ~ ~ 3 3 3 0.5 50 force
execute at @s[scores={em.timer=105}] run function emissary:attacks/phase2/sword_plunge/__generated__/block/334
execute at @s[scores={em.timer=110..}] run function emissary:attacks/phase2/sword_plunge/explode
execute at @s[tag=slow] positioned over world_surface run function emissary:attacks/phase2/sword_plunge/__generated__/block/337