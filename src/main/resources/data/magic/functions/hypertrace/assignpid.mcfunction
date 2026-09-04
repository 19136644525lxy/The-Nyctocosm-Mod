#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s m.hypertrace.pid matches 1.. run function magic:hypertrace/__generated__/block/11
scoreboard players operation m.global m.hypertrace.pid = @s m.hypertrace.pid