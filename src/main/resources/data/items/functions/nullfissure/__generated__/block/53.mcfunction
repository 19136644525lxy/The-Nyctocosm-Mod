#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add fissurehit
scoreboard players set damageAmt value 15500
tag @e[tag=damagetemp,limit=1] add damager
execute as @s run function items:nullfissure/__generated__/block/54