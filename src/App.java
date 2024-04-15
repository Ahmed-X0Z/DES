import java.util.Scanner;

public class App {

    public static String[] plainText_StringToBinary(String[] plainText_Parts, int numOfParts) {
        String[] plainText_Binary;
        plainText_Binary = new String[numOfParts];
        char c;
        String binaryChar;
        for (int i = 0; i < numOfParts; i++) {
            plainText_Binary[i] = "";
            for (int j = 0; j < 8; j++) {
                c = plainText_Parts[i].charAt(j);
                binaryChar = Integer.toBinaryString(c);
                while (binaryChar.length() < 8) {
                    binaryChar = "0" + binaryChar;

                }
                plainText_Binary[i] = plainText_Binary[i] + binaryChar;
            }

        }
        return plainText_Binary;
    }

    public static String key_StringToBinary(String key) {
        String key_Binary;
        key_Binary = "";
        char c;
        String binaryChar;
        for (int i = 0; i < key.length(); i++) {
            c = key.charAt(i);
            binaryChar = Integer.toBinaryString(c);
            while (binaryChar.length() < 8) {
                binaryChar = "0" + binaryChar;
            }
            key_Binary = key_Binary + binaryChar;

        }
        return key_Binary;
    }

    public static String[] keyGeneration(String key) {
        String keyAfter_pc1 = "";
        String keyAfter_pc2 = "";
        String keyL, keyR;
        String[][] generatedKeys_Splited;
        generatedKeys_Splited = new String[16][2];
        String[] generatedKeys;
        generatedKeys = new String[16];
        int[] pc_1 = { 57, 49, 41, 33, 25, 17, 9, 1,
                58, 50, 42, 34, 26, 18, 10, 2,
                59, 51, 43, 35, 27, 19, 11, 3,
                60, 52, 44, 36, 63, 55, 47, 39,
                31, 23, 15, 7, 62, 54, 46, 38,
                30, 22, 14, 6, 61, 53, 45, 37,
                29, 21, 13, 5, 28, 20, 12, 4 };

        int[] pc_2 = { 14, 17, 11, 24, 1, 5,
                3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32 };

        for (int i = 0; i < 56; i++) {
            keyAfter_pc1 = keyAfter_pc1 + key.charAt(pc_1[i] - 1);
        }

        keyL = keyAfter_pc1.substring(0, 28);
        keyR = keyAfter_pc1.substring(28, 56);

        for (int i = 0; i < 16; i++) {
            if ((i + 1) == 1 || (i + 1) == 2 || (i + 1) == 9 || (i + 1) == 16) {
                keyL = leftShift(keyL, 1);
                keyR = leftShift(keyR, 1);
                generatedKeys_Splited[i][0] = keyL;
                generatedKeys_Splited[i][1] = keyR;

            } else {
                keyL = leftShift(keyL, 2);
                keyR = leftShift(keyR, 2);
                generatedKeys_Splited[i][0] = keyL;
                generatedKeys_Splited[i][1] = keyR;
            }
        }
        for (int i = 0; i < 16; i++) {
            generatedKeys[i] = generatedKeys_Splited[i][0] + generatedKeys_Splited[i][1];
        }
        for (int i = 0; i < 16; i++) {
            keyAfter_pc2 = "";
            for (int j = 0; j < 48; j++) {
                keyAfter_pc2 = keyAfter_pc2 + generatedKeys[i].charAt(pc_2[j] - 1);
            }
            generatedKeys[i] = keyAfter_pc2;
        }

        return generatedKeys;
    }

    public static String leftShift(String key, int amount) {
        return key.substring(amount) + key.substring(0, amount);
    }

    public static String[] Encryption(String[] plainText_Binary, String[] keys, int numOfParts) {
        int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7 };
        String TokenAfterIP;
        String[] plainTextTokens;
        plainTextTokens = new String[numOfParts];
        String[][] plainTextTokens_Splited;
        plainTextTokens_Splited = new String[numOfParts][2];
        String[][] encryptedTokens_Splited;
        encryptedTokens_Splited = new String[numOfParts][2];
        String tokenLeft;
        String tokenRight;
        String temp = "";
        String[] Swaped;
        Swaped = new String[numOfParts];
        String[] EncryptedText;
        EncryptedText = new String[numOfParts];
        int[] IP_inverse = { 40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25 };

        for (int i = 0; i < numOfParts; i++) {
            TokenAfterIP = "";
            for (int j = 0; j < 64; j++) {
                TokenAfterIP = TokenAfterIP + plainText_Binary[i].charAt(IP[j] - 1);
            }
            plainTextTokens[i] = TokenAfterIP;
        }

        for (int i = 0; i < numOfParts; i++) {
            plainTextTokens_Splited[i][0] = plainTextTokens[i].substring(0, 32);
            plainTextTokens_Splited[i][1] = plainTextTokens[i].substring(32, 64);
        }

        for (int i = 0; i < numOfParts; i++) {
            tokenLeft = plainTextTokens_Splited[i][0];
            tokenRight = plainTextTokens_Splited[i][1];

            for (int j = 0; j < 16; j++) {
                temp = tokenLeft;
                tokenLeft = tokenRight;
                tokenRight = XOR_Strings(temp, f_Function(tokenRight, keys[j]));
            }
            encryptedTokens_Splited[i][0] = tokenLeft;
            encryptedTokens_Splited[i][1] = tokenRight;
        }
        for (int i = 0; i < numOfParts; i++) {
            temp = encryptedTokens_Splited[i][0];
            encryptedTokens_Splited[i][0] = encryptedTokens_Splited[i][1];
            encryptedTokens_Splited[i][1] = temp;
            Swaped[i] = encryptedTokens_Splited[i][0] + encryptedTokens_Splited[i][1];

        }

        for (int i = 0; i < numOfParts; i++) {
            EncryptedText[i] = "";
            for (int j = 0; j < 64; j++) {
                EncryptedText[i] = EncryptedText[i] + Swaped[i].charAt(IP_inverse[j] - 1);
            }

        }
        return EncryptedText;
    }

    public static String f_Function(String tokenRight, String roundKey) {
        String Right = "";
        int[] E = { 32, 1, 2, 3, 4, 5,
                4, 5, 6, 7, 8, 9,
                8, 9, 10, 11, 12, 13,
                12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21,
                20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29,
                28, 29, 30, 31, 32, 1 };
        String XOR_Result;
        String[] XOR_Result_Tokens;
        XOR_Result_Tokens = new String[8];
        int row, col;
        int[][][] SBoxes = { { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
                { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                        { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                        { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                        { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
                { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                        { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                        { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                        { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
                { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                        { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                        { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                        { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
                { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                        { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                        { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                        { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
                { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                        { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                        { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                        { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
                { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                        { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                        { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                        { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
                { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                        { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                        { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                        { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };
        StringBuilder SBox_Result = new StringBuilder();

        int[] finalP = { 16, 7, 20, 21, 29, 12, 28, 17,
                1, 15, 23, 26, 5, 18, 31, 10,
                2, 8, 24, 14, 32, 27, 3, 9,
                19, 13, 30, 6, 22, 11, 4, 25, };
        String f_Function_Result = "";

        for (int i = 0; i < 48; i++) {
            Right = Right + tokenRight.charAt(E[i] - 1);
        }

        XOR_Result = XOR_Strings(Right, roundKey);

        for (int i = 0; i < 8; i++) {
            XOR_Result_Tokens[i] = XOR_Result.substring(i * 6, (i + 1) * 6);
        }

        for (int i = 0; i < 8; i++) {
            row = Integer.parseInt(XOR_Result_Tokens[i].substring(0, 1) + XOR_Result_Tokens[i].substring(5), 2);
            col = Integer.parseInt(XOR_Result_Tokens[i].substring(1, 5), 2);
            int SBoxValue = SBoxes[i][row][col];
            String binaryString = Integer.toBinaryString(SBoxValue);
            while (binaryString.length() < 4) {
                binaryString = "0" + binaryString;
            }
            SBox_Result.append(binaryString);
        }
        for (int i = 0; i < 32; i++) {
            f_Function_Result = f_Function_Result + SBox_Result.charAt(finalP[i] - 1);
        }
        return f_Function_Result;
    }

    public static String XOR_Strings(String str1, String str2) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str1.length(); i++) {
            int bit1 = Character.getNumericValue(str1.charAt(i));
            int bit2 = Character.getNumericValue(str2.charAt(i));
            int xor = bit1 ^ bit2;
            result.append(xor);
        }
        return result.toString();
    }

    // Decryption

    public static String[] Decryption(String[] encryptedText, String[] keys, int numOfParts) {
        // Reversing the encryption process
        int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64,
                56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37,
                29,
                21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
        String TokenAfterIP;
        String[] encryptedTextTokens;
        encryptedTextTokens = new String[numOfParts];
        String[][] encryptedTextTokensSplited;
        encryptedTextTokensSplited = new String[numOfParts][2];
        String[][] decryptedTokensSplited;
        decryptedTokensSplited = new String[numOfParts][2];
        String tokenLeft;
        String tokenRight;
        String temp = "";
        String[] swapped;
        swapped = new String[numOfParts];
        String[] decryptedText;
        decryptedText = new String[numOfParts];
        int[] IP_inverse = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62,
                30,
                37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42,
                10,
                50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25 };

        for (int i = 0; i < numOfParts; i++) {
            TokenAfterIP = "";
            for (int j = 0; j < 64; j++) {
                TokenAfterIP = TokenAfterIP + encryptedText[i].charAt(IP[j] - 1);
            }
            encryptedTextTokens[i] = TokenAfterIP;
        }

        for (int i = 0; i < numOfParts; i++) {
            encryptedTextTokensSplited[i][0] = encryptedTextTokens[i].substring(0, 32);
            encryptedTextTokensSplited[i][1] = encryptedTextTokens[i].substring(32, 64);
        }

        for (int i = 0; i < numOfParts; i++) {
            tokenLeft = encryptedTextTokensSplited[i][0];
            tokenRight = encryptedTextTokensSplited[i][1];

            for (int j = 15; j >= 0; j--) {
                temp = tokenLeft;
                tokenLeft = tokenRight;
                tokenRight = XOR_Strings(temp, f_Function(tokenRight, keys[j]));
            }
            decryptedTokensSplited[i][0] = tokenLeft;
            decryptedTokensSplited[i][1] = tokenRight;
        }

        for (int i = 0; i < numOfParts; i++) {
            temp = decryptedTokensSplited[i][0];
            decryptedTokensSplited[i][0] = decryptedTokensSplited[i][1];
            decryptedTokensSplited[i][1] = temp;
            swapped[i] = decryptedTokensSplited[i][0] + decryptedTokensSplited[i][1];
        }

        for (int i = 0; i < numOfParts; i++) {
            decryptedText[i] = "";
            for (int j = 0; j < 64; j++) {
                decryptedText[i] = decryptedText[i] + swapped[i].charAt(IP_inverse[j] - 1);
            }
        }

        String[] decryptedPlainText = new String[numOfParts];
        for (int i = 0; i < numOfParts; i++) {
            StringBuilder plainTextBuilder = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                String byteStr = decryptedText[i].substring(j * 8, (j + 1) * 8);
                int charCode = Integer.parseInt(byteStr, 2);
                if (charCode == 45) {
                    break;
                }
                plainTextBuilder.append((char) charCode);
            }
            decryptedPlainText[i] = plainTextBuilder.toString();
        }

        return decryptedPlainText;
    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        // plain text

        System.out.println("Enter the plain text: ");
        String plainText = input.nextLine();

        while (plainText.length() % 8 != 0) {
            plainText += "-";
        }

        int plainText_Length = plainText.length();
        int numOfParts = (plainText_Length / 8);

        String[] plainText_Parts;
        plainText_Parts = new String[numOfParts];

        for (int i = 0; i < numOfParts; i++) {
            int start = i * 8;
            int end = Math.min((i + 1) * 8, plainText_Length);
            plainText_Parts[i] = plainText.substring(start, end);

        }

        String[] plainText_Binary;
        plainText_Binary = new String[numOfParts];
        plainText_Binary = plainText_StringToBinary(plainText_Parts, numOfParts);

        System.out.println("-----------------------------------------");
        System.out.println("Binary Plain Text: ");
        for (int i = 0; i < numOfParts; i++) {
            System.out.println(plainText_Binary[i]);

        }
        System.out.println("-----------------------------------------");

        // KEY

        System.out.println("Enter a key of 8 chars: ");
        String KEY = input.nextLine();

        while (KEY.length() != 8) {
            System.out.println("Invalid key length.");
            System.out.println("Enter a key of 8 chars: ");
            KEY = input.nextLine();
        }

        String KEY_Binary = key_StringToBinary(KEY);
        System.out.println("-----------------------------------------");
        System.out.println("Binary KEY: " + KEY_Binary);
        String[] keys = keyGeneration(KEY_Binary);

        // Encryption
        String[] EncryptedText = Encryption(plainText_Binary, keys, numOfParts);

        String[] EncryptedPlainText = new String[numOfParts];
        for (int i = 0; i < numOfParts; i++) {
            StringBuilder plainTextBuilder = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                String byteStr = EncryptedText[i].substring(j * 8, (j + 1) * 8);
                int charCode = Integer.parseInt(byteStr, 2);
                if (charCode == 45) {
                    break;
                }
                plainTextBuilder.append((char) charCode);
            }
            EncryptedPlainText[i] = plainTextBuilder.toString();
        }
        String cipherText = "";
        for (int i = 0; i < numOfParts; i++) {
            cipherText = cipherText + EncryptedPlainText[i];
        }
        System.out.println("-----------------------------------------");
        System.out.println("Cipher Text: ");
        System.out.println(cipherText);
        System.out.println("-----------------------------------------");

        // Decryption
        String[] DecryptedText = Decryption(EncryptedText, keys, numOfParts);
        String DecryptedPlainText = "";
        for (int i = 0; i < numOfParts; i++) {
            DecryptedPlainText = DecryptedPlainText + DecryptedText[i];
        }

        System.out.println("Decrypted Text: " + DecryptedPlainText);
    }
}
