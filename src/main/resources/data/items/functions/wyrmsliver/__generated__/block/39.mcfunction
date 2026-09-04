#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add sliverhit
scoreboard players set damageAmt value 14000
tag @e[tag=damagetemp,limit=1] add damager
execute as @s run function items:wyrmsliver/__generated__/block/40
effect give @s blindness 5 0 true