#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage = @e[tag=Gravitance,tag=projectile,distance=..6] s.damage
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @a[tag=damagetemp,limit=1] add damager
execute as @s run function spells:__generated__/block/79