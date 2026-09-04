#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s positioned over ocean_floor unless entity @e[tag=crypt,tag=slatehenge,tag=structure,distance=..200] run place structure nyctocosm:night_wastes/slatehenge ~ ~ ~
kill @s