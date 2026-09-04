#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s daisID = Tester daisID
execute if block ~ ~ ~ anvil[facing=north] run tp @s ~ ~ ~ ~90 ~
execute if block ~ ~ ~ anvil[facing=south] run tp @s ~ ~ ~ ~90 ~
tag @s remove justsummoned