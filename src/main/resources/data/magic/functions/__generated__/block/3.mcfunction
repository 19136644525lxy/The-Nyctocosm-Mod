#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=hypertrace,tag=aj.ajhypertrace.root,tag=m.hypertraceMatched] positioned ~ ~-0.35 ~ run tp @s ~ ~ ~ 0 0
execute store success score m.interactor.success value run data get entity @s interaction
execute if score m.interactor.success value matches 1 run function magic:__generated__/block/4
scoreboard players reset m.interactor.success value
execute store success score m.interactor.success value run data get entity @s attack
execute if score m.interactor.success value matches 1 run function magic:__generated__/block/7
scoreboard players reset m.interactor.success value