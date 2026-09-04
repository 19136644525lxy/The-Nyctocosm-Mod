#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score m.temp2.UUID0 value run data get entity @s data.UUID[0]
execute store result score m.temp2.UUID1 value run data get entity @s data.UUID[1]
execute store result score m.temp2.UUID2 value run data get entity @s data.UUID[2]
execute store result score m.temp2.UUID3 value run data get entity @s data.UUID[3]
execute if score m.temp.UUID0 value = m.temp2.UUID0 value if score m.temp.UUID1 value = m.temp2.UUID1 value if score m.temp.UUID2 value = m.temp2.UUID2 value if score m.temp.UUID3 value = m.temp2.UUID3 value run kill @s