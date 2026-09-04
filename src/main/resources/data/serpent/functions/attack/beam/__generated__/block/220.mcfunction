#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set damageAmt value 18000
tag @e[tag=damagetemp,limit=1] add damager
execute as @s run function serpent:attack/beam/__generated__/block/221
effect give @s darkness 15 0