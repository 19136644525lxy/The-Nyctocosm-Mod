#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage = lightningfielddamage em.value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @s add damagetemp
tag @e[tag=damagetemp,limit=1] add damager
execute as @a[distance=..4,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase3/lightning_field/__generated__/block/621
tag @s remove damagetemp
summon lightning_bolt
particle explosion ~ ~ ~ 0.4 0.4 0.4 0 10 force
particle flash ~ ~1 ~ 0 0 0 0 1 force
kill @s