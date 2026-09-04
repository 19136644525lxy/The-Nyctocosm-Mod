#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add fissurehit
effect give @s slowness 10 1 true
scoreboard players set damageAmt value 15500
tag @e[tag=damagetemp,limit=1] add damager
execute as @s run function items:nullfissure/__generated__/block/46