#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage = fireballdamage em.value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @e[tag=emissary,tag=emissarymodel,limit=1] add damager
execute as @s run function emissary:attacks/phase4/fireball/__generated__/block/706