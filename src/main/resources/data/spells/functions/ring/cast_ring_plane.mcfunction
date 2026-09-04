#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s[tag=!a.transposition] unless entity @e[tag=spell,tag=origin,tag=plane,distance=..2] run function spells:ring/__generated__/block/532
execute at @s[tag=a.transposition] unless entity @e[tag=spell,tag=origin,tag=plane,distance=..2] run function spells:ring/__generated__/block/536