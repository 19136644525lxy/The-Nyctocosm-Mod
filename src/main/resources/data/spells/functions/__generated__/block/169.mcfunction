#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage = @s s.damage
scoreboard players operation damageAmt value = @s ncd_damage
execute as @e[tag=matchedid,limit=1] run function damage:playermarkertag
tag @a[tag=damagetemp,limit=1] add damager
execute as @s run function spells:__generated__/block/170
tag @a remove damagetemp