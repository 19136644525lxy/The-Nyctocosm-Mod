#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add scythehit
execute store result score TempHealth value run data get entity @s Health 1000
execute if score TempHealth value matches ..11000 run function items:scythe/__generated__/block/31
scoreboard players reset TempHealth value
scoreboard players set damageAmt value 11000
tag @e[tag=damagetemp,limit=1] add damager
execute as @s run function items:scythe/__generated__/block/32