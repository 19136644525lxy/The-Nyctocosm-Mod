#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion ~ ~1 ~ 0.2 0.2 0.2 0 5
scoreboard players operation @s ncd_damage = swordplungedamage em.value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @e[tag=emissary,tag=attack,tag=sword_plunge,sort=nearest,limit=1] add damager
execute as @s run function emissary:attacks/phase2/sword_plunge/__generated__/block/339
tag @s remove impact_swordplunge