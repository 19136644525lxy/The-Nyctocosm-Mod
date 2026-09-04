#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=m.dealt_damage.void] run function artifacts:absorption/absorb
scoreboard players operation a.TEMP m.hypertrace.pid = @s m.hypertrace.pid
tag @s add a.Owner
execute as @e[tag=hypertrace,tag=aj.ajhypertrace.root] if score @s m.hypertrace.pid = a.TEMP m.hypertrace.pid at @s run function artifacts:absorption/__generated__/block/6
tag @s remove a.Owner